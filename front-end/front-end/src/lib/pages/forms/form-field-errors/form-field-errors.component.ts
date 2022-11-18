import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-field-errors',
  templateUrl: './form-field-errors.component.html',
  styleUrls: ['./form-field-errors.component.scss'],
})
export class FormFieldErrorsComponent {
  @Input() validationErrors: Record<string, string[]> | null = null;
  @Input() fieldName: string = '';

  public isFieldHasErrors(): boolean {
    if (this.isValidationErrorsInvalid()) {
      return false;
    }

    return this.validationErrors![this.fieldName]?.length !== 0;
  }

  public getFieldErrors(): string[] {
    if (this.isValidationErrorsInvalid()) {
      return [];
    }

    return this.validationErrors![this.fieldName];
  }

  private isValidationErrorsInvalid(): boolean {
    return this.validationErrors === null || this.validationErrors === undefined;
  }
}
