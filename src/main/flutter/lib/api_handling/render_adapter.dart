import 'package:flutter/material.dart';
import 'package:share/share.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';

class RenderAdapterListDataTable {
  static ApiService ultraService = ApiService();
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
                  leading: IconButton(
                      icon: Icon(Icons.directions_car),
                    onPressed: (){
                        Share.share("Hey, look at my cool car: http://$ApiService.ip_of_service/ultra-api/read-single?id=" +item.id.toString());
                    },
                  ),
                  title: Text(item.id.toString()),
                );
              },
              body: Column(
                children: [
                  ListTile(
                    subtitle: DataTable(
                      columns: [
                        DataColumn(label: Text('Key')),
                        DataColumn(label: Text('Value')
                        )
                      ],
                      rows: itemAttributesAsRows,
                    ),
                  ),
                  Row(
                    children: [
                      Spacer(),
                      FlatButton(
                        onPressed: (){
                          print("Ehhhm....");
                          //force reload
                          ultraService.deleteAuto(item.id);
                          autosAsItems.remove(item);
                          setState();
                        },
                        child: Text(
                          'DELETE',
                          style: TextStyle(
                            color: Colors.red.shade600
                          ),
                        ),
                      ),
                    ],
                  )
                ],
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
