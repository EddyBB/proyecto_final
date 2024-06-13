import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComprasUsuarioModalComponent } from './compras-usuario-modal.component';

describe('ComprasUsuarioModalComponent', () => {
  let component: ComprasUsuarioModalComponent;
  let fixture: ComponentFixture<ComprasUsuarioModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComprasUsuarioModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComprasUsuarioModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
