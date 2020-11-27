import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:ultra_car_service_app/api_handling/api_service.dart';
import 'package:ultra_car_service_app/pages/create.dart';
import 'package:ultra_car_service_app/pages/launch_screen.dart';
import 'package:ultra_car_service_app/pages/list_all.dart';
import 'package:ultra_car_service_app/pages/search.dart';
import 'package:ultra_car_service_app/pages/update.dart';

void main() {
  ApiService.loadIp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: ThemeData(
            primaryColor: Colors.purple.shade900,
            floatingActionButtonTheme: FloatingActionButtonThemeData(
              backgroundColor: Colors.purple.shade600,
            ),
            elevatedButtonTheme: ElevatedButtonThemeData(style: ButtonStyle(
                backgroundColor: MaterialStateProperty.resolveWith<Color>(
                    (Set<MaterialState> states) {
              if (states.contains(MaterialState.pressed))
                return Colors.purple.shade600;
              return Colors.purple.shade600;
            })))),
        title: "Ultra CarService App",
        home: HomePage(),
      )
    ;
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;
  void _onSelect(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  _getBodyContents(int index) {
    if (index == 0) {
      return GetAllPage();
    } else if (index == 1) {
      return SearchSinglePage();
    } else if (index == 2) {
      return CreatePage();
    } else if (index == 3) {
      return UpdatePage();
    } else {
      return Text('where the fuck are you?');
    }
  }

  void checkIfIPSet() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String savedIp = prefs.getString('ip');
    if (savedIp == null){
      Navigator.push(context, MaterialPageRoute(builder: (context) {
        return LaunchPage();
      }));
      prefs.setString('ip', ApiService.ip_of_service);
    }
    else {
      ApiService.ip_of_service = savedIp;
    }
  }

  @override
  void initState() {
    super.initState();
    //Default init ip to
    ApiService.ip_of_service = '192.168.0.104:8080';
    // Timer(Duration(seconds: 1), () => checkIfIPSet());
  }

  @override
  Widget build(BuildContext context) {
    SystemChrome.setSystemUIOverlayStyle(
      SystemUiOverlayStyle(
          statusBarColor: Colors.transparent
      )
    );
    return Scaffold(
      appBar: AppBar(
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text("Ultra CarService App"),
            FlatButton(
              onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (context) {return LaunchPage();})),
              child: Text('Set IP',
              style: TextStyle(
                fontWeight: FontWeight.normal,
                fontSize: 15.0,
                color: Colors.white,
              )),
            )
          ],
        ),
      ),
      body: _getBodyContents(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'List All'),
          BottomNavigationBarItem(icon: Icon(Icons.search), label: 'Search'),
          BottomNavigationBarItem(icon: Icon(Icons.add), label: 'Add'),
          BottomNavigationBarItem(icon: Icon(Icons.edit), label: 'Edit'),
        ],
        currentIndex: _selectedIndex,
        onTap: _onSelect,
        type: BottomNavigationBarType.fixed,
      ),
    );
  }
}
