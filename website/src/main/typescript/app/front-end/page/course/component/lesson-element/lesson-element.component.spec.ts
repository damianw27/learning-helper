import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LessonElementComponent } from './lesson-element.component';

describe('LessonElementComponent', () => {
  let component: LessonElementComponent;
  let fixture: ComponentFixture<LessonElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LessonElementComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LessonElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
