import { Injectable } from '@nestjs/common';
import { CategoryRepo } from 'src/models/repo/category.repo';

@Injectable()
export class CategoryService {
  constructor(private categoryRepo: CategoryRepo) {}

  async getAllCategories() {
    return {
      status: 200,
      message: 'Get all categories successfully!',
      metadata: { categories: await this.categoryRepo.getAllCategories() },
    };
  }

  async getCategory(category_id: string) {
    const checkCategoryExists =
      await this.categoryRepo.checkCategoryExists(category_id);
    return {
      status: 200,
      message: 'Get category successfully!',
      metadata: {
        category: await this.categoryRepo.getCategory(category_id),
      },
    };
  }

  async addCategory(body: { name: string; image: string }) {
    return {
      status: 201,
      message: 'Add category successfully!',
      metadata: {
        category: await this.categoryRepo.addCategory(body),
      },
    };
  }

  async updateCategory(
    body: { name: string; image: string },
    category_id: string,
  ) {
    const checkCategoryExists =
      await this.categoryRepo.checkCategoryExists(category_id);
    return await this.categoryRepo.updateCategory(body, category_id);
  }
  async deleteCategory(category_id: string) {
    const checkCategoryExists =
      await this.categoryRepo.checkCategoryExists(category_id);
    await this.categoryRepo.deleteCategory(category_id);
  }
}
