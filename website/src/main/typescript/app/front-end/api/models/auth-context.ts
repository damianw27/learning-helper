export interface AuthContext {
	readonly type: string;
	readonly token: string;
	readonly xsrfToken: string;
}

export const defaultAuthContext: AuthContext = {
	type: 'none',
	token: '',
	xsrfToken: '',
};
