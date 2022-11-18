import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RichTextEditor } from './rich-text-editor.component';

describe('RichTextEditorComponent', () => {
  let component: RichTextEditor;
  let fixture: ComponentFixture<RichTextEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RichTextEditor],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RichTextEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
