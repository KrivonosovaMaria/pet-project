import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TrackService} from "../track.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NavigateDirective} from "../../navigate.directive";

@Component({
	selector: 'app-track-add',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './track-add.component.html',
	standalone: true
})

export class TrackAddComponent implements OnInit {

	trackFormGroup = new FormGroup({
		name: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		address: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		length: new FormControl(null, [Validators.required, Validators.min(0.01), Validators.max(1000000)]),
	})

	img: any = null;

	constructor(
		private router: Router,
		private global: GlobalService,
		private trackService: TrackService,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
	}

	changeImg(event: any) {
		this.img = event.target.files;
	}

	checkSubmit(): boolean {
		if (this.trackFormGroup.invalid) return false;
		if (this.img === null) return false;

		return true;
	}

	save() {
		this.trackService.save(this.trackFormGroup.value, this.img);
	}

}
