import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Automobile} from '../automobile';
import {UltraCarServiceService} from '../ultra-car-service.service';

@Component({
  selector: 'app-automobile-edit',
  templateUrl: './automobile-edit.component.html',
  styleUrls: ['./automobile-edit.component.css']
})
export class AutomobileEditComponent implements OnInit {

  myForm: FormGroup;
  foundAuto$: Automobile;
  currentID = 0;

  constructor(private fb: FormBuilder, private autoService: UltraCarServiceService) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      m_automobileAttributes: this.fb.array([])
    });

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
    submittedInfoAsAutomobile.m_id = this.currentID;
    if (this.currentID !== 0){
      this.autoService.updateSomething(submittedInfoAsAutomobile);
    }
  }

  search(term: string): void {
    this.autoService.searchSomething(term).subscribe(data => {
      // @ts-ignore
      this.foundAuto$ = data;
      if (data != null){

        while (this.autoAttributesForms.length !== 0){
          this.autoAttributesForms.removeAt(0);
        }
        for (const attrKeys of Object.keys((data as unknown as Automobile).m_automobileAttributes)) {
          this.addNewItem((data as unknown as Automobile).m_automobileAttributes[attrKeys], attrKeys);
        }
        this.currentID = (data as unknown as Automobile).m_id;
      }
    });
  }
}
