import { Component, OnInit } from '@angular/core';

import {Automobile} from '../automobile';
import {UltraCarServiceService} from '../ultra-car-service.service';

@Component({
  selector: 'app-automobile-search',
  templateUrl: './automobile-search.component.html',
  styleUrls: ['./automobile-search.component.css']
})
export class AutomobileSearchComponent implements OnInit {

  foundAuto$: Automobile;

  constructor(private autoService: UltraCarServiceService) { }

  ngOnInit(): void {
  }

  search(term: string): void {
    this.autoService.searchSomething(term).subscribe(data => {
      // @ts-ignore
      this.foundAuto$ = data;
    });
  }
}
