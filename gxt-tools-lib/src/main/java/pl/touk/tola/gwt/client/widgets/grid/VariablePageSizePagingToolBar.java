/*
 * Copyright (c) 2008 TouK.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.touk.tola.gwt.client.widgets.grid;

import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import pl.touk.tola.gwt.client.widgets.ComboBoxWithMemory;

import java.util.List;
import java.util.ArrayList;

/**
 * Nieco zmodyfikowany paging toolbar. Dodaje on dodatkowo mozliwosc ustawiania ilosci wierszywyswietlanych na stronie.
 * Combo wyswieltajace opcje wyboru jest zbudowane na bazie ComboBoxWithMemory wiec zapamietuje w cistac ostatnio wybrany
 * index
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 * @since 0.3.1
 */
public class VariablePageSizePagingToolBar extends PagingToolBar implements TolaPagingToolbar  {
    private ChoosePageSizeComboBox pageSizeComboBox;

    /**
     * Jedyny konstruktor
     *
     * @param uniqueName unikalna nazwa wykorzystana jako klucz do zapisu wybranej wartosci w cisteczkach
     */
    public VariablePageSizePagingToolBar() {

        super(PageSize.DEFAULT_SET.get(0).getSize());
        add(new SeparatorToolItem());
        add(new LabelToolItem("Wyników na stronie:"));
        pageSizeComboBox = new ChoosePageSizeComboBox();
//      jesli pageSizeComboBox zwroci -1 to znaczy ze nie znalazlo w ciastach poprzednio zaznaczonego indexu
        if (pageSizeComboBox.getGridPageSize() != -1) {

            this.setPageSize(pageSizeComboBox.getGridPageSize());

        } else {
            pageSizeComboBox.selectFirstOption();
            this.setPageSize(pageSizeComboBox.getGridPageSize());
        }
        
        pageSizeComboBox.setWidth(60);
        add(pageSizeComboBox);
    }

    @Override
    public void setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        getPageSizeComboBox().setRawValue(""+pageSize);
    }

    @Override
    protected void afterRender() {
        super.afterRender();
//  przy zmianie wartosci w comboboxie zawsze przewijaj do pierwszej strony i ustaw nowo wybrana wartosc jako page size
        getPageSizeComboBox().addListener(Events.Select, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent be) {
                VariablePageSizePagingToolBar.this.setPageSize(getPageSizeComboBox().getValue().getSize());
                VariablePageSizePagingToolBar.this.first();

            }
        });
//        pageSizeComboBox.addSelectionChangedListener(new SelectionChangedListener<PageSize>() {
//            @Override
//            public void selectionChanged(SelectionChangedEvent<PageSize> se) {
//                if (se.getType() == Events.Select) {
//                    VariablePageSizePagingToolBar.this.setPageSize(pageSizeComboBox.getValue().getSize());
//                    VariablePageSizePagingToolBar.this.first();
//                }
//            }
//        });

    }

    /**
     * @return the pageSizeComboBox
     */
    public ChoosePageSizeComboBox getPageSizeComboBox() {
        return pageSizeComboBox;
    }

    public void selectFirstOption() {
        pageSizeComboBox.selectFirstOption();
    }

    public void selectLastOption() {
        pageSizeComboBox.selectLastOption();
    }

    public int getGridPageSize() {
        return pageSizeComboBox.getGridPageSize();
    }

    /**
     * Zmodyfikowany ComboBoxWithMemory tak aby przechowywal tylko wartosci wyboru dla ilosci
     * wierszy na stronie
     */
    public static class ChoosePageSizeComboBox extends ComboBox<PageSize> {

        protected ListStore<PageSize> pageSizeStore = new ListStore<PageSize>();

        /**
         * Ustawia store z wyborem elementow inny niz domyslny
         */
        public void setPageSizeStore(ListStore pageSizeStore) {
            this.pageSizeStore = pageSizeStore;
        }

        public ChoosePageSizeComboBox() {
            super();
            pageSizeStore.add(PageSize.DEFAULT_SET);
            this.setStore(pageSizeStore);
            this.setEditable(false);
            this.setDisplayField("size");
            this.setTriggerAction(TriggerAction.ALL);

        }

        /**
         * Wybiera pierwszy element z combo. Pamietajcie ze ta akcja strzela eventami
         */
        public void selectFirstOption(){
            this.setValue(getStore().getAt(0));
        }

        public void selectLastOption() {
            this.setValue(getStore().getAt(5));
        }

        // jesli value jest nullem tzn ze w ciastkach podczas inicializacji klasa bazowa nie znalazla wartosci
        // elementu ktory powinien byc zaznaczony
        public int getGridPageSize(){
            return getValue() != null ? getValue().getSize() : -1;
        }

    }

    
}
