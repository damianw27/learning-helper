import { UserBean } from '../../api/models/user-bean';
import {AuthContext} from "../../api/models/auth-context";

export interface AuthState {
  readonly user: UserBean;
  readonly authContext: AuthContext;
  readonly errorMessage?: string;
}
