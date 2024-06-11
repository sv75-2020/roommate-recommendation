import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendedRoommatesComponent } from './recommended-roommates.component';

describe('RecommendedRoommatesComponent', () => {
  let component: RecommendedRoommatesComponent;
  let fixture: ComponentFixture<RecommendedRoommatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecommendedRoommatesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecommendedRoommatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
