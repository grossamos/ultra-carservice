import 'package:flutter/material.dart';
import 'package:ultra_car_service_app/pages/create.dart';
import 'package:ultra_car_service_app/pages/list_all.dart';
import 'package:ultra_car_service_app/pages/search.dart';
import 'package:ultra_car_service_app/pages/update.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Ultra CarService App",
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;
  void _onSelect (int index){
    setState(() {
      _selectedIndex = index;
    });
  }
  
  _getBodyContents(int index){
    if (index == 0){
      return GetAllPage();
    }
    else if (index == 1){
      return SearchSinglePage();
    }
    else if (index == 2){
      return CreatePage();
    }
    else if (index == 3){
      return UpdatePage();
    }
    else {
      return Text('where the fuck are you?');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Ultra CarService App"),
      ),
      body: _getBodyContents(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'List All'
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.search),
            label: 'Search'
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.add),
            label: 'Add'
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.edit),
            label: 'Edit'
          ),
        ],
        currentIndex: _selectedIndex,
        onTap: _onSelect,
        type: BottomNavigationBarType.fixed,
      ),
    );
  }
}



