import { UserRole } from './enums/user-role';

export interface UserUpdateBean {
  readonly id: number;
  readonly displayName: string;
  readonly email: string;
  readonly userRole: UserRole;
}
