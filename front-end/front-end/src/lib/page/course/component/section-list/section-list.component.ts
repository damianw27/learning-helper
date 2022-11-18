import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Section } from '../../../../model/section.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-section-list',
  templateUrl: './section-list.component.html',
  styleUrls: ['./section-list.component.scss'],
})
export class SectionListComponent implements OnInit {
  @Input() courseId: number = -1;
  @Input() isEditMode: boolean = false;
  public sections!: Section[];

  constructor(private httpClient: HttpClient, private changeDetectorRef: ChangeDetectorRef) {}

  public ngOnInit(): void {
    if (this.courseId === null) {
      return;
    }

    const request$ = this.httpClient.get<Section[]>(`/api/section/by-course/${this.courseId}`);

    firstValueFrom(request$)
      .then(sections => (this.sections = sections))
      .catch(() => (this.sections = []))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  onSectionDelete(deletedSection: Section) {
    this.sections = [...this.sections].filter(currentSection => currentSection.id !== deletedSection.id);
    this.changeDetectorRef.detectChanges();
  }

  onAddNewSection(section: Section) {
    this.sections.push(section);
    this.changeDetectorRef.detectChanges();
  }
}
