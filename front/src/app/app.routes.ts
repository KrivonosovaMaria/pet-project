import {Routes} from '@angular/router';
import {ErrorComponent} from "./error/error.component";
import {StatsComponent} from "./stats/stats.component";
import {UserComponent} from "./user/user.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegComponent} from "./auth/reg/reg.component";
import {MainComponent} from "./main/main.component";
import {TrackComponent} from "./track/track.component";
import {TrackPageComponent} from "./track/track-page/track-page.component";
import {TrackAddComponent} from "./track/track-add/track-add.component";
import {TrackUpdateComponent} from "./track/track-update/track-update.component";
import {RaceComponent} from "./race/race.component";
import {RacePageComponent} from "./race/race-page/race-page.component";
import {RaceAddComponent} from "./race/race-add/race-add.component";
import {RaceUpdateComponent} from "./race/race-update/race-update.component";
import {TicketComponent} from "./ticket/ticket.component";
import {NotificationComponent} from "./notification/notification.component";

export const routes: Routes = [

	{path: "", component: MainComponent},

	{path: "reg", component: RegComponent},
	{path: "login", component: LoginComponent},

	{path: "users", component: UserComponent},

	{path: "tracks", component: TrackComponent},
	{path: "track", component: TrackPageComponent},
	{path: "track_add", component: TrackAddComponent},
	{path: "track_update", component: TrackUpdateComponent},

	{path: "races", component: RaceComponent},
	{path: "race", component: RacePageComponent},
	{path: "race_add", component: RaceAddComponent},
	{path: "race_update", component: RaceUpdateComponent},

	{path: "tickets", component: TicketComponent},

	{path: "notifications", component: NotificationComponent},

	{path: "stats", component: StatsComponent},

	{path: "error", component: ErrorComponent},
	{path: "**", component: ErrorComponent},

];
