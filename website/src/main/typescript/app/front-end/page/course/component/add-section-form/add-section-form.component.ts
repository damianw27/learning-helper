import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Section } from '../../../../model/section.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-add-section-form',
  templateUrl: './add-section-form.component.html',
  styleUrls: ['./add-section-form.component.scss'],
})
export class AddSectionFormComponent implements OnChanges {
  @Input() courseId!: number;

  @Output() addedNewSection = new EventEmitter<Section>();

  sectionFrom = this.formBuilder.group({
    courseId: [this.courseId, Validators.required],
    title: ['', Validators.required],
    cardinalIndex: [0, Validators.required],
  });

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public ngOnChanges(changes: SimpleChanges): void {
    const courseIdChanged = changes['courseId']?.currentValue;

    if (courseIdChanged === undefined) {
      return;
    }

    this.sectionFrom.setValue({
      courseId: courseIdChanged,
      title: '',
      cardinalIndex: 0,
    });

    this.changeDetectorRef.detectChanges();
  }

  public onFormSubmit(event: SubmitEvent): void {
    if (this.sectionFrom.invalid) {
      console.error(this.sectionFrom.value);
      return;
    }

    const request$ = this.httpClient.post<Section>(
      '/api/section/create',
      this.sectionFrom.value
    );

    firstValueFrom(request$)
      .then((section) => this.addedNewSection.emit(section))
      .then(() => this.resetFormValues());

    event.preventDefault();
  }

  public getTitleClassName(): string {
    return this.isTitleInvalid() ? 'form-control is-invalid' : 'form-control';
  }

  private isTitleInvalid(): boolean {
    return this.sectionFrom.touched && this.sectionFrom.invalid;
  }

  private resetFormValues(): void {
    this.sectionFrom.reset();

    this.sectionFrom.setValue({
      courseId: this.courseId,
      title: '',
      cardinalIndex: 0,
    });

    this.changeDetectorRef.detectChanges();
  }
}
