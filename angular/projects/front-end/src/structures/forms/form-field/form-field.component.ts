import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
})
export class FormFieldComponent {
  @Input() inputId!: string;
  @Input() inputName!: string;
  @Input() labelMessage!: string;
  @Input() validationErrors!: Record<string, string[]> | null;
}
