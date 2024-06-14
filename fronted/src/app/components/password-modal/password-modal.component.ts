import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-password-modal',
  templateUrl: './password-modal.component.html',
  styleUrls: ['./password-modal.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class PasswordModalComponent {
  @Output() close = new EventEmitter<string>();
  passwordForm: FormGroup;
  passwordsMismatch = false;
  private subscription: Subscription;

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<PasswordModalComponent>) {
    this.passwordForm = this.fb.group({
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });

    this.subscription = this.passwordForm.valueChanges.subscribe(() => {
      this.passwordsMismatch = this.passwordForm.value.newPassword !== this.passwordForm.value.confirmPassword;
    });
  }

  submit() {
    if (this.passwordForm.valid && !this.passwordsMismatch) {
      this.close.emit(this.passwordForm.value.newPassword);
      this.dialogRef.close(this.passwordForm.value.newPassword);
    }
  }

  closeModal() {
    this.dialogRef.close();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
