export interface Category {
  id?: number;
  name: string;
  subCategories: SubCategory[];
}

export interface SubCategory {
  id?: number;
  name: string;
}

export interface CategoryState {
  categories: Category[];
  form: {
    status: 'idle' | 'loading' | 'success' | 'error';
    message: string;
  };
}
