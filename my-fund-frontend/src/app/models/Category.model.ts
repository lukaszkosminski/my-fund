export interface Category {
  id?: string;
  name: string;
  subCategories: SubCategory[];
}

export interface SubCategory {
  name: string;
}

export interface CategoryState {
  categories: Category[];
  form: {
    status: 'idle' | "loading" | "success" | "error";
    message: string;
  }
}