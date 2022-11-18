import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormActionsComponent } from './form-actions/form-actions.component';
import { FormFieldComponent } from './form-field/form-field.component';
import { FormFieldErrorsComponent } from './form-field-errors/form-field-errors.component';

@NgModule({
  declarations: [
    FormActionsComponent,
    FormFieldComponent,
    FormFieldErrorsComponent,
  ],
  exports: [FormActionsComponent, FormFieldComponent, FormFieldErrorsComponent],
  imports: [CommonModule],
})
export class FormsModule {}
