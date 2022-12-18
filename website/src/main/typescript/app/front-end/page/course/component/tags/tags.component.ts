import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Input,
} from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss'],
})
export class TagsComponent {
  @Input() tags: FormControl = new FormControl([]);

  public addTagForm = this.formBuilder.group({
    tag: [''],
  });

  constructor(
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public onNewTagSubmit($event: SubmitEvent) {
    this.tags.setValue([
      ...(this.tags?.value ?? []),
      this.addTagForm.value.tag,
    ]);
    this.addTagForm.reset();
    this.changeDetectorRef.detectChanges();
    $event.preventDefault();
  }

  public onRemoveTag(tag: string) {
    const updatedTags = [...this.tags.value].filter(
      (currentTag) => currentTag !== tag
    );
    this.tags.setValue(updatedTags);
    this.changeDetectorRef.detectChanges();
  }
}
