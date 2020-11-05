import { Component, OnInit } from '@angular/core';
import {Automobile} from '../../Automobile';
import {UltraCarServiceService} from '../ultra-car-service.service';

@Component({
  selector: 'app-automobile-list',
  templateUrl: './automobile-list.component.html',
  styleUrls: ['./automobile-list.component.css']
})
export class AutomobileListComponent implements OnInit {

  constructor(private ultraService: UltraCarServiceService) { }

  myAutomobile: Automobile;

  myAutomobiles: any[];

  ngOnInit(): void {
    this.ultraService.getListOfCars().subscribe(data => this.myAutomobiles = data);
  }

  deleteAutomobile(id: number): void{
    this.ultraService.deleteSomething(id);
    this.ultraService.getListOfCars().subscribe(data => this.myAutomobiles = data);
  }

}
