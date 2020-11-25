import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;

class ApiService {
  static String ip_of_service;

  Future<List> getList() async {
    http.Response response = await http.get(
        "http://$ip_of_service/ultra-api/read-all",
        headers: {
          "Accept" : "application/json"
        }
    );
    List automobileList = jsonDecode(response.body);
    return  automobileList;
  }

  Future searchAuto(int id) async {
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
    return jsonDecode(response.body);
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