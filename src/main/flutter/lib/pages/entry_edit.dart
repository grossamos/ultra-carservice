import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';

Form getEditForm(GlobalKey<FormState> formKey, var storedAttributes,
    BuildContext context, Function() setStateOfForm, Function() onSave) {
  return Form(
    key: formKey,
    child: Column(
      children: [
        Expanded(
          child: ListView.builder(
            shrinkWrap: true,
            itemCount: storedAttributes['m_automobileAttributes'].length,
            itemBuilder: (context, index) {
              // var debug = storedAttributes['m_automobileAttributes'].keys.toList()
              final currentKey = storedAttributes['m_automobileAttributes']
                  .keys
                  .toList()[index];
              // return Text('hello');
              return Padding(
                padding: const EdgeInsets.all(8.0),
                child: Row(
                  children: [
                    Text(currentKey),
                    SizedBox(
                      width: 20.0,
                    ),
                    Expanded(
                      child: TextFormField(
                        decoration: const InputDecoration(hintText: 'Value'),
                        validator: (value) {
                          if (value.isEmpty) {
                            return 'Please enter a value';
                          } else {
                            storedAttributes['m_automobileAttributes']
                                [currentKey] = value.toString();
                            return null;
                          }
                        },
                      ),
                    ),
                    SizedBox(
                      width: 20.0,
                    ),
                    IconButton(
                      icon: Icon(Icons.delete),
                      onPressed: (){
                        storedAttributes['m_automobileAttributes'].remove(currentKey);
                        formKey.currentState.validate();
                        setStateOfForm();
                      },
                    )
                  ],
                ),
              );
            },
          ),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            Padding(
              padding: EdgeInsets.all(10.0),
              child: ElevatedButton(
                onPressed: () {
                  final secondFormKey = GlobalKey<FormState>();
                  String newFieldName;
                  showDialog(
                      context: context,
                      builder: (context) {
                        return AlertDialog(
                          title: Text('Enter name of Field'),
                          content: Form(
                            key: secondFormKey,
                            child: TextFormField(
                                decoration:
                                    const InputDecoration(hintText: 'Value'),
                                validator: (value) {
                                  if (value.isEmpty) {
                                    return 'Please enter a value';
                                  } else {
                                    newFieldName = value.toString();
                                    return null;
                                  }
                                }),
                          ),
                          actions: [
                            FlatButton(
                              onPressed: () {
                                secondFormKey.currentState.validate();
                                storedAttributes['m_automobileAttributes']
                                    [newFieldName] = '';
                                setStateOfForm();
                                Navigator.of(context).pop();
                              },
                              child: Text('Submit'),
                            )
                          ],
                        );
                      });
                },
                child: Text('Add Field'),
              ),
            ),
            Padding(
              padding: EdgeInsets.all(10.0),
              child: ElevatedButton(
                onPressed: () {
                  onSave();
                },
                child: Text('Save'),
              ),
            ),
          ],
        ),
      ],
    ),
  );
}
