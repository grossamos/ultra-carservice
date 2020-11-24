import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/api_handling/render_adapter.dart';

class SearchSinglePage extends StatefulWidget {
  @override
  SearchSinglePageState createState() {
    return SearchSinglePageState();
  }

}

class SearchSinglePageState extends State<SearchSinglePage>{
  int _givenId;
  ApiService ultraService = ApiService();
  final _formKey = GlobalKey<FormState>();

  Widget _onSubmit(int id){
    if (id == null){
      return Container();
    }
    return Container(
      width: double.infinity,
      child: FutureBuilder(
        future: ultraService.searchAuto(id),
        builder: (context, snapshot){
          if (snapshot.data == null){
            return Container(
              child: Text("No automobiles with that Id found"),
            );
          }
          else{
            return SingleChildScrollView(
                child: RenderAdapterListDataTable.getDataTable([snapshot.data])
            );
          }
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return  Padding(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 30.0),
      child: Column(
        children: [
          Container(
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    decoration: const InputDecoration(hintText: 'Enter the Id of your Automobile'),
                    validator: (value){
                      if (value.isEmpty){
                        return 'Please enter an id';
                      }
                      // ignore: deprecated_member_use
                      else if (int.parse(value.toString(), onError: (e) => null) == null){
                        return 'Id has to be an int';
                      }
                      else {
                        _givenId = int.parse(value.toString());
                        return null;
                      }
                    },
                  ),
                  Padding(
                    padding: EdgeInsets.all(10.0),
                    child: ElevatedButton(
                      onPressed: (){
                        if (_formKey.currentState.validate()){
                          setState(() { });
                        }
                      },
                      child: Text('Search'),
                    ),
                  )
                ],
              ),
            ),
          ),
          _onSubmit(_givenId)
        ],
      ),
    );
  }
}