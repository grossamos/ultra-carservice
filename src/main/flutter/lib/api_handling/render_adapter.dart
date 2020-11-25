import 'package:flutter/material.dart';

class RenderAdapterListDataTable {
  static List<UltraCardListItem> autosAsItems;

  static List<UltraCardListItem> listToItems(List autos){
    List<UltraCardListItem> returnList = [];
    for (var auto in autos){
      returnList.add(UltraCardListItem(auto['m_id'], auto['m_automobileAttributes'], expanded: (autos.length == 1)));
    }
    return returnList;
  }

  static listToExpandedView(List autos, Function() setState) {
    if (autosAsItems == null || (autosAsItems != null && autos[0]['m_id'] != autosAsItems[0].id || autos.length != autosAsItems.length ))
      autosAsItems = listToItems(autos);
    return SingleChildScrollView(
      child: Container(
        child: ExpansionPanelList(
          expansionCallback: (int index, bool isExpanded) {
            autosAsItems[index].expanded = !isExpanded;
            setState();
          },
          children: autosAsItems.map<ExpansionPanel>((UltraCardListItem item) {
            List<DataRow> itemAttributesAsRows = [];
            for (String key in item.attributes.keys.toList()) {
              itemAttributesAsRows.add(DataRow(cells: [
                DataCell(Text(key)),
                DataCell(Text(item.attributes[key]))
              ]));
            }
            return ExpansionPanel(
              headerBuilder: (BuildContext context, bool isExpanded) {
                return ListTile(
                  leading: Icon(Icons.directions_car),
                  title: Text(item.id.toString()),
                );
              },
              body: ListTile(
                subtitle: DataTable(
                  columns: [
                    DataColumn(label: Text('Key')),
                    DataColumn(label: Text('Value')
                    )
                  ],
                  rows: itemAttributesAsRows,
                ),
              ),
              isExpanded: item.expanded,
            );
          }).toList(),
        ),
      ),
    );
  }
}

class UltraCardListItem {
  UltraCardListItem(int id, Map attributes, {bool expanded}) {
    this.id = id;
    this.attributes = attributes;
    if (expanded == null) {
      this.expanded = expanded;
    } else {
      this.expanded = false;
    }
  }

  int id;
  Map attributes;
  bool expanded = false;
}
