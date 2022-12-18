import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';
import { Section } from '../../../../model/section.model';
import { HttpClient } from '@angular/common/http';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-section-element',
  templateUrl: './section-element.component.html',
  styleUrls: ['./section-element.component.scss'],
})
export class SectionElementComponent implements OnChanges {
  @Input() section!: Section;
  @Input() isEditMode: boolean = false;

  @Output() sectionDelete = new EventEmitter<Section>();

  sectionForm = this.formBuilder.group({
    id: [undefined, [Validators.required]],
    courseId: [undefined, [Validators.required]],
    title: [undefined, Validators.required],
    description: [undefined, Validators.required],
    cardinalIndex: [undefined, Validators.required],
  });

  constructor(
    private httpClient: HttpClient,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public ngOnChanges(changes: SimpleChanges): void {
    const sectionChange = changes['section']?.currentValue;

    if (sectionChange === undefined) {
      return;
    }

    this.sectionForm.setValue({
      id: sectionChange.id,
      courseId: sectionChange.courseId,
      title: sectionChange.title,
      description: sectionChange.description.content,
      cardinalIndex: sectionChange.cardinalIndex,
    });
  }

  public getDescriptionFormControl(): FormControl {
    return <FormControl>this.sectionForm.controls['description'];
  }

  public onFormSubmit(): void {
    const request$ = this.httpClient.post<Section>(
      `/api/section/update`,
      this.sectionForm.value
    );

    firstValueFrom(request$)
      .then((updatedSection) =>
        this.updateSectionAndRefreshView(updatedSection)
      )
      .catch(console.error);
  }

  private updateSectionAndRefreshView(updatedSection: Section) {
    this.section = updatedSection;
    this.changeDetectorRef.detectChanges();
  }

  onDeleteSection() {
    const reuqest$ = this.httpClient.delete(`/api/section/${this.section.id}`);
    firstValueFrom(reuqest$).then(() => this.sectionDelete.emit(this.section));
  }
}
