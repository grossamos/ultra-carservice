import { Component, OnInit } from '@angular/core';
import {Automobile} from '../../Automobile';
import {UltraCarServiceService} from '../ultra-car-service.service';
import {interval, Subscription} from 'rxjs';

@Component({
  selector: 'app-automobile-list',
  templateUrl: './automobile-list.component.html',
  styleUrls: ['./automobile-list.component.css']
})
export class AutomobileListComponent implements OnInit {

  constructor(private ultraService: UltraCarServiceService) { }

  myAutomobiles: any[];

  subscription: Subscription;


  ngOnInit(): void {
    this.updateCars();
    // emit value in sequence every 10 second
    const source = interval(2000);
    this.subscription = source.subscribe(val => this.updateCars());
  }

  updateCars(): void {
    this.ultraService.getListOfCars().subscribe(data => this.myAutomobiles = data);
  }

  deleteAutomobile(id: number): void{
    this.ultraService.deleteSomething(id);
    this.ultraService.getListOfCars().subscribe(data => this.myAutomobiles = data);
  }

}
