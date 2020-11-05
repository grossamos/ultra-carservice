import { Component, OnInit } from '@angular/core';
import {Automobile} from '../automobile';
import {UltraCarServiceService} from '../ultra-car-service.service';
import {FormGroup, FormBuilder, FormArray} from '@angular/forms';

@Component({
  selector: 'app-automobile-create',
  templateUrl: './automobile-create.component.html',
  styleUrls: ['./automobile-create.component.css']
})
export class AutomobileCreateComponent implements OnInit {

  myForm: FormGroup;
  currentAutomobile: Automobile = new Automobile();

  constructor(private ultraService: UltraCarServiceService, private fb: FormBuilder) { }


  ngOnInit(): void {
    this.myForm = this.fb.group({
      m_automobileAttributes: this.fb.array([])
    });
    this.addNewItem('', 'Name');
    this.addNewItem('', 'Model');
  }

  get autoAttributesForms(): FormArray{
    return this.myForm.get('m_automobileAttributes') as FormArray;
  }

  addNewItem(value?: string, key?: string): void {
    const valuePairFormGroup = this.fb.group({
      key,
      value
    });

    this.autoAttributesForms.push(valuePairFormGroup);
  }

  deleteValuePair(i): void{
    this.autoAttributesForms.removeAt(i);
  }

  saveAuto(): void{
    const submittedInfo = this.myForm.getRawValue().m_automobileAttributes;
    const submittedInfoAsAutomobile: Automobile = new Automobile();
    for (let i = 0; i < submittedInfo.length; i++) {
      if (submittedInfo[i].key !== null){
        submittedInfoAsAutomobile.m_automobileAttributes[submittedInfo[i].key] = submittedInfo[i].value;
        submittedInfoAsAutomobile.m_automobileAttributes[submittedInfo[i].key] = submittedInfo[i].value;
      }
    }
    this.ultraService.addSomething(submittedInfoAsAutomobile);
  }
}
