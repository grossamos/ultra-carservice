import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LaunchPage extends StatefulWidget {
  Function() returnToMainState;
  @override
  _LaunchPageState createState() => _LaunchPageState();

  LaunchPage();
}

class _LaunchPageState extends State<LaunchPage> {
  @override
  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    return Scaffold(
      backgroundColor: Colors.purple.shade800,
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(50.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                'Please enter the IP of your Ultra CarService',
                style: TextStyle(
                  color: Colors.yellow.shade50,
                  fontSize: 25.0,
                ),
                textAlign: TextAlign.center,
              ),
              SizedBox(
                height: 30.0,
              ),
              Form(
                key: _formKey,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Expanded(
                      child: TextFormField(
                          style: TextStyle(
                            color: Colors.white,
                          ),
                          decoration: const InputDecoration(
                              hintText: 'IP of Ultra CarService',
                              hintStyle: TextStyle(
                                color: Colors.white60,
                              )),
                          validator: (value) {
                            if (value.isEmpty) {
                              return 'Please enter an IP';
                            } else {
                              ApiService.setIp(value);

                              return null;
                            }
                          }),
                    ),
                    Expanded(
                      child: FlatButton(
                        child: Text(
                          'Save',
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                        onPressed: () {
                          if (_formKey.currentState.validate()) {
                            Navigator.pop(context);
                          }
                        },
                      ),
                    )
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
