import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/api_handling/render_adapter.dart';

class SearchSinglePage extends StatefulWidget {
  @override
  SearchSinglePageState createState() {
    return SearchSinglePageState();
  }
}

class SearchSinglePageState extends State<SearchSinglePage> {
  String _givenKey;
  ApiService ultraService = ApiService();
  final _formKey = GlobalKey<FormState>();

  Widget _onSubmit(String key) {
    if (key == null) {
      return Container();
    }
    return FutureBuilder(
        future: ultraService.properSearchAuto(key),
        builder: (context, snapshot) {
          if (snapshot.data == null) {
            return Container(
              child: Text("No automobiles with that Id found"),
            );
          }
          return Expanded(
            child: SingleChildScrollView(
                child: RenderAdapterListDataTable.listToExpandedView(
                    snapshot.data, () => setState(() {}))),
          );
        });
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      child: Column(
        children: [
          Card(
            elevation: 5.0,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Form(
                key: _formKey,
                child: ListTile(
                    title: TextFormField(
                      decoration:
                          const InputDecoration(hintText: 'Id of Automobile'),
                      validator: (value) {
                        if (value.isEmpty) {
                          return 'Please enter an id';
                        } else {
                          _givenKey = value.toString();
                          return null;
                        }
                      },
                    ),
                    trailing: ElevatedButton(
                      onPressed: () {
                        if (_formKey.currentState.validate()) {
                          setState(() {});
                        }
                      },
                      child: Text('Search'),
                    )),
              ),
            ),
          ),
          SizedBox(
            height: 20.0,
          ),
          _onSubmit(_givenKey)
        ],
      ),
    );
  }
}
