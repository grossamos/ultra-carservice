import 'package:flutter/material.dart';

class RenderAdapterListDataTable {
  static const standardDataColumn = <DataColumn>[
    DataColumn(
        label: Text(
      'Id',
    )),
    DataColumn(
        label: Text(
      'Name',
    )),
    DataColumn(
        label: Text(
      'Attributes',
    ))
  ];

  static getDataTable(List list) {
    var dataRows = <DataRow>[];
    for (var auto in list) {
      dataRows.add(listItemToDataCell(auto));
    }
    return DataTable(columns: standardDataColumn, rows: dataRows);
  }

  static listItemToDataCell(auto) {
    // Process Attributes, to display as list
    Map attributesAsMap = auto['m_automobileAttributes'];
    List attributesAsList = new List();
    String attributesAsString = '';
    for (var attributeKey in attributesAsMap.keys) {
      attributesAsList.add([attributeKey, attributesAsMap[attributeKey]]);
      attributesAsString += attributeKey[0].toUpperCase() + attributeKey.substring(1)  + ' = ' + attributesAsMap[attributeKey] + '\n';
    }
    ListView attributesListView = ListView.builder(
      itemCount: attributesAsList.length,
      itemBuilder: (context, index) {
        return Text(
            attributesAsList[index][0][0].toUpperCase() + attributesAsList[index][0].substring(1) + ":\t" + attributesAsList[index][0]);
      },
    );

    // return actual Data row
    return DataRow(cells: <DataCell>[
      DataCell(
        Text(auto['m_id'].toString()),
      ),
      DataCell(Text(auto['m_automobileAttributes']['name'].toString())),
      DataCell(
        Text(attributesAsString),
      )
    ]);
  }
}
