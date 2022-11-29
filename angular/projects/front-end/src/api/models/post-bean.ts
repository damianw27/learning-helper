export interface PostBean {
  readonly id: number;
  readonly title: string;
  readonly description: ContentBean;
  readonly contributors: UserBean[];
  readonly subPosts: SubPostBean[];
  readonly tags: TabBean[];
  readonly created: string;
  readonly updated: string;
}
