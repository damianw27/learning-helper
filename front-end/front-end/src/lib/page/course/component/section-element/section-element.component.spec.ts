import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionElementComponent } from './section-element.component';

describe('SectionElementComponent', () => {
  let component: SectionElementComponent;
  let fixture: ComponentFixture<SectionElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SectionElementComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
