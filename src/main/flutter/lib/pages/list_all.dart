import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/api_handling/render_adapter.dart';

class GetAllPage extends StatefulWidget {
  @override
  GetAllPageState createState() {
    return GetAllPageState();
  }

}

class GetAllPageState extends State<GetAllPage>{
  @override
  Widget build(BuildContext context) {
    ApiService ultraService = ApiService();
    return  Container(
      width: double.infinity,
      child: FutureBuilder(
        future: ultraService.getList(),
        builder: (context, snapshot){
          if (snapshot.data == null){
            return Container(
              child: Text("Loading..."),
            );
          }
          else{
            return RefreshIndicator(
              onRefresh: () async {
                await Future.delayed(Duration(seconds: 2)).then((onvalue) {
                  setState(() {
                  });
                });
                return 'Refreshed';
              },
              child: SingleChildScrollView(
                  child: Padding(
                    padding: EdgeInsets.all(20.0),
                    child: RenderAdapterListDataTable.listToExpandedView(snapshot.data, () => setState((){})),
                  ) 
              ),
            );
          }
        },
      ),
    );
  }
}
