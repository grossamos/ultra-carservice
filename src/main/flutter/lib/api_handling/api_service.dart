import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class ApiService {
  static String ip_of_service;

  static void setIp(String ip) async{
    print("hey!");
    ip_of_service = ip;
    SharedPreferences preferences = await SharedPreferences.getInstance();
    await preferences.setString("ip", ip);
    print(preferences.getString("ip"));
  }

  static void loadIp() async{
    //doesn't work as of now
    SharedPreferences preferences = await SharedPreferences.getInstance();
    ip_of_service = preferences.getString("ip");
    print(preferences.getString("ip"));
  }

  Future<List> getList() async {
    //doesn't work, sadly :(
    http.Response response = await http.get(
        "http://$ip_of_service/ultra-api/read-all",
        headers: {
          "Accept" : "application/json"
        }
    );
    List automobileList = jsonDecode(response.body);
    return  automobileList;
  }

  Future searchAuto(int  id) async {
    http.Response response = await http.get(
      'http://$ip_of_service/ultra-api/read-single?id=$id',
      headers: {
        "Accept" : "application/json"
      }
    );
    var responseAsObj = jsonDecode(response.body);
    if (responseAsObj['status'] == 400){
      return null;
    }
    return responseAsObj;
  }

  Future properSearchAuto(String searchKey) async {
    searchKey.replaceAll(" ", "%20");
    http.Response response = await http.get(
      'http://$ip_of_service/ultra-api/search?key=$searchKey',
      headers: {
        "Accept" : "application/json"
      }
    );

    return jsonDecode(response.body);
  }

  void deleteAuto(int id) async {
    http.Response response = await http.delete(
      'http://$ip_of_service/ultra-api/delete-car?id=$id',
      headers: {
        "Accept" : "application/json"
      }
    );
  }

  void saveAuto(Map automobileAsMap) async {
    http.Response response = await http.post(
        'http://$ip_of_service/ultra-api/create-car',
        headers: {
          "Content-Type" : "application/json"
        },
        body: jsonEncode(automobileAsMap),
    );
  }

  void updateAuto(Map automobileAsMap, int id) async {
    http.Response response = await http.put(
      'http://$ip_of_service/ultra-api/update-car?id=$id',
      headers: {
        "Content-Type" : "application/json"
      },
      body: jsonEncode(automobileAsMap),
    );
  }
}