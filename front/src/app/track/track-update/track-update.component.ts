import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TrackService} from "../track.service";

@Component({
	selector: 'app-track-update',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './track-update.component.html',
	standalone: true
})

export class TrackUpdateComponent implements OnInit {

	id: number = 0;

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
		private activatedRoute: ActivatedRoute,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);

		this.activatedRoute.queryParams.subscribe(params => {
			this.id = params['id'];
			this.trackService.find(params['id']).subscribe({
				next: (res: any) => {
					this.trackFormGroup.setValue({
						name: res.data.name,
						address: res.data.address,
						length: res.data.length,
					})
				},
				error: (e: any) => {
					console.log(e.error)
					if (e.error.code === 404) {
						this.router.navigate(['/error'], {queryParams: {message: e.error.message}});
					} else {
						this.router.navigate(['/login']);
					}
				}
			})
		})
	}

	changeImg(event: any) {
		this.img = event.target.files;
	}

	checkSubmit(): boolean {
		if (this.trackFormGroup.invalid) return false;

		return true;
	}

	update() {
		this.trackService.update(this.id, this.trackFormGroup.value, this.img);
	}

}
