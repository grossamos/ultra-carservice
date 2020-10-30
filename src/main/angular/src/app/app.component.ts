import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Ultra CarService Client';
  readonly rootApiUrl: string = 'http://localhost:8080/ultra-api';

  constructor() {
  }
}
