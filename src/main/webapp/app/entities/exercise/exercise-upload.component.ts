import { Component, OnInit, OnDestroy } from '@angular/core';
import { ExercisePopupService } from './exercise-popup.service';
import { ActivatedRoute } from '@angular/router';
import { ExerciseService } from './exercise.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-exercise-upload',
  templateUrl: './exercise-upload.component.html',
  styles: []
})
export class ExerciseUploadComponent implements OnInit {

  fileToUpload: FileList;

  constructor(public activeModal: NgbActiveModal, private exerciseService: ExerciseService) { }

  ngOnInit() {
  	console.log("Exercise upload init..");
  }
  
  noteFile(fileList: FileList) {
  	this.fileToUpload = fileList;
  }
   
  uploadFile() {
	  if(this.fileToUpload && this.fileToUpload.length > 0) {
	     let file : File = this.fileToUpload.item(0); 
	       console.log(file.name);
	       let reader: FileReader = new FileReader();
	       reader.readAsText(file);
	       reader.onload = (e) => {
	          let csv: string = reader.result;
	          console.log(csv);
	          this.exerciseService.uploadExercises(csv).subscribe((result) => {
	            console.log('Success');
	            this.clear();
	        }, (reason) => {
	            console.log('Failed ' + reason);
	            this.clear();
	        });
	      }
	  }
  }
  
  clear() {
    this.activeModal.dismiss('cancel');
  }
}

@Component({
    selector: 'jhi-upload-popup',
    template: ''
})
export class ExerciseUploadPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private exercisePopupService: ExercisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
             this.exercisePopupService.open(ExerciseUploadComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
