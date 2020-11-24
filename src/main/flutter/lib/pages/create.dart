import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/pages/entry_edit.dart';

class CreatePage extends StatefulWidget {
  @override
  _CreatePageState createState() => _CreatePageState();
}

class _CreatePageState extends State<CreatePage> {
  ApiService ultraService = ApiService();
  final _formKey = GlobalKey<FormState>();

  Map storedAttributes;

  void initAttributes() {
    storedAttributes = Map();
    storedAttributes['m_automobileAttributes'] = Map();
    storedAttributes['m_automobileAttributes']['name'] = '';
    storedAttributes['m_automobileAttributes']['model'] = '';
  }

  @override
  Widget build(BuildContext context) {
    if (storedAttributes == null) {
      initAttributes();
    }
    return getEditForm(_formKey, storedAttributes, context, () {
      setState(() {});
    }, () {
      if (_formKey.currentState.validate()) {
        setState(() {
          _formKey.currentState.reset();
        });
        // ignore: deprecated_member_use
        Scaffold.of(context).showSnackBar(SnackBar(content: Text('Saved')));
        ultraService.saveAuto(storedAttributes);
        storedAttributes = null;
      }
    });
  }
}
