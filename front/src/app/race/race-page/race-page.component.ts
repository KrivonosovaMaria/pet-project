import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RaceService} from "../race.service";
import {NavigateDirective} from "../../navigate.directive";
import {NgIf} from "@angular/common";
import {TicketService} from "../../ticket/ticket.service";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {RaceCommentService} from "../race-comment.service";

@Component({
	selector: 'app-race-page',
	imports: [
		NavigateDirective,
		NgIf,
		FormsModule,
		ReactiveFormsModule
	],
	templateUrl: './race-page.component.html',
	standalone: true
})

export class RacePageComponent implements OnInit {

	race: any = {
		name: ''
	};

	count: any = null;

	eventDate: any;
	daysLeft: number | null = null;

	commentFormGroup = new FormGroup({
		text: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(5000)]),
	})

	get comments() {
		return this.race.comments;
	}

	constructor(
		private global: GlobalService,
		private activatedRoute: ActivatedRoute,
		private router: Router,
		private raceService: RaceService,
		private ticketService: TicketService,
		private commentService: RaceCommentService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.activatedRoute.queryParams.subscribe(params => {
			this.raceService.find(params['id']).subscribe({
				next: (res: any) => {
					this.race = res.data
					this.eventDate = new Date(this.race.date);
					this.calculateDaysLeft();
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

	delete() {
		this.raceService.delete(this.race.id);
	}

	ticket() {
		this.ticketService.save(this.count, this.race.id).subscribe({
			next: () => {
				this.count = null;
				this.raceService.find(this.race.id).subscribe({
					next: (res: any) => this.race = res.data,
					error: (e: any) => this.global.error(e)
				})
			},
			error: (e: any) => this.global.error(e)
		})
	}

	calculateDaysLeft() {
		const today = new Date();
		today.setHours(0, 0, 0, 0);

		const diffInMs = this.eventDate.getTime() - today.getTime();
		this.daysLeft = Math.floor(diffInMs / (1000 * 60 * 60 * 24));
	}

	getDayWord(days: number): string {
		if (days % 10 === 1 && days % 100 !== 11) return 'день';
		if (days % 10 >= 2 && days % 10 <= 4 && (days % 100 < 10 || days % 100 >= 20)) return 'дня';
		return 'дней';
	}

	comment() {
		this.commentService.save(this.commentFormGroup.value, this.race.id).subscribe({
			next: (res: any) => {
				this.comments.unshift(res.data);
				this.commentFormGroup.reset();
			},
			error: (e: any) => this.global.error(e),
		})
	}

}
