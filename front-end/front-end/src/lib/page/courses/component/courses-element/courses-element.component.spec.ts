import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesElementComponent } from './courses-element.component';

describe('CoursesElementComponent', () => {
  let component: CoursesElementComponent;
  let fixture: ComponentFixture<CoursesElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoursesElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CoursesElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
