import { Component, Inject, Optional } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-reason-dialog',
  templateUrl: './reason-dialog.component.html',
  styleUrls: ['./reason-dialog.component.css']
})
export class ReasonDialogComponent {
  reason:string | undefined;

  constructor(
    public dialogRef: MatDialogRef<ReasonDialogComponent>,
    //@Optional() is used to prevent error if no data is passed
    @Optional() @Inject(MAT_DIALOG_DATA) public data: string) {
    console.log(data);
    this.reason = data;
  }

  getReason(){
    this.dialogRef.close({data:this.reason});
  }

}
