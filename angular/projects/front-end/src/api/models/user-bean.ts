import { UserRole } from './enums/user-role';

export interface UserBean {
  readonly id: number;
  readonly name: string;
  readonly displayName: string;
  readonly email: string;
  readonly userRole: UserRole;
  readonly created: string;
  readonly updated: string;
}
