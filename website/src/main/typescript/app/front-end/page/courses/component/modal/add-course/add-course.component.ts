import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Form, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AddCourseStore } from './add-course.store';

@Component({
  selector: 'app-modal-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.scss'],
  providers: [AddCourseStore],
})
export class AddCourseComponent {
  public courseForm = this.formBuilder.group({
    title: ['', Validators.required],
    description: [''],
  });

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private addCourseStore: AddCourseStore
  ) {}

  public onSubmit(modal: NgbActiveModal): void {
    this.addCourseStore.createCourse([modal, this.courseForm.value]);
  }

  public getDescriptionFormControl(): FormControl {
    return this.courseForm.controls['description'] as FormControl;
  }
}
