import { UserBean } from './user-bean';
import { AuthContext } from './auth-context';

export interface UserAuthBean {
  readonly authenticated: boolean;
  readonly user: UserBean;
  readonly authContext?: AuthContext;
}
