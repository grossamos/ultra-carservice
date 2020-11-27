import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/pages/entry_edit.dart';

class UpdatePage extends StatefulWidget {
  @override
  _UpdatePageState createState() => _UpdatePageState();
}

class _UpdatePageState extends State<UpdatePage> {
  ApiService ultraService = ApiService();
  final _formKey = GlobalKey<FormState>();
  final _formKeyTwo = GlobalKey<FormState>();
  int idOfCar;
  Map automobileMap;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15.0, vertical: 10.0),
          child: Card(
            elevation: 5,
            child: Form(
              key: _formKey,
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: ListTile(
                  title: TextFormField(
                    decoration: const InputDecoration(hintText: 'Value'),
                    validator: (value) {
                      if (value.isEmpty) {
                        return 'Please enter the Id of your Auto';
                      } else {
                        setState(() {
                          idOfCar = int.parse(value.toString());
                        });
                        return null;
                      }
                    },
                  ),
                    trailing: ElevatedButton(
                      child: Text('Search'),
                      onPressed: () {
                        _formKey.currentState.validate();
                      },
                    )
                ),
              ),
            ),
          ),
        ),
        Expanded(
          child: FutureBuilder(
            future: ultraService.searchAuto(idOfCar),
            builder: (context, snapshot) {
              if (automobileMap == null){
                automobileMap = snapshot.data;
              }
              return getEditFromMap(
                automobileMap,
                idOfCar,
              );
            },
          ),
        )
      ],
    );
  }

  Widget getEditFromMap(
      Map auto,
      int id,
      ) {
    if (auto == null && id == null) {
      return Container();
    } else if (auto == null) {
      return Text('No automobiles with that id found');
    } else {
      return getEditForm(_formKeyTwo, auto, context, () {
        setState(() {});
      }, () {
        if (_formKeyTwo.currentState.validate()){
          // ignore: deprecated_member_use
          Scaffold.of(context).showSnackBar(SnackBar(content: Text('Saved')));
          ultraService.updateAuto(auto, id);
          automobileMap = null;
        }
      });

    }
  }
}
