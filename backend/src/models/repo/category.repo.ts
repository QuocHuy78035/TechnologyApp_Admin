import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Category } from '../category.model';
import { BadRequestException } from '@nestjs/common';

export class CategoryRepo {
  constructor(
    @InjectModel('Category') private categoryModel: Model<Category>,
  ) {}

  async checkCategoryExists(category_id: string) {
    const category = await this.categoryModel.findById(category_id);
    if (!category) {
      throw new BadRequestException(
        `Category with is ${category_id} is not found!`,
      );
    }
    return category;
  }

  async getAllCategories() {
    return await this.categoryModel.find();
  }

  async getCategory(category_id: string) {
    return await this.categoryModel.findById(category_id);
  }
  async addCategory(body: { name: string; image: string }) {
    return await this.categoryModel.create(body);
  }

  async updateCategory(
    body: { name: string; image: string },
    category_id: string,
  ) {
    return await this.categoryModel.findByIdAndUpdate(category_id, body, {
      new: true,
    });
  }
  async deleteCategory(category_id: string) {
    await this.categoryModel.findByIdAndDelete(category_id);
  }
}
