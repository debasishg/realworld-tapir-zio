package com.softwaremill.realworld.articles

import com.softwaremill.realworld.profiles.ProfilesRepository
import com.softwaremill.realworld.tags.TagsRepository
import com.softwaremill.realworld.users.UsersRepository
import com.softwaremill.realworld.utils.DbData.*
import zio.ZIO

object ArticleDbTestSupport:

  private def prepareTags(articleRepo: ArticlesRepository, article1Id: Int, article2Id: Int) = {
    for {
      _ <- articleRepo.addTag("dragons", article1Id)
      _ <- articleRepo.addTag("training", article1Id)
      _ <- articleRepo.addTag("dragons", article2Id)
      _ <- articleRepo.addTag("goats", article2Id)
      _ <- articleRepo.addTag("training", article2Id)
    } yield ()
  }

  private def prepareFavorites(articleRepo: ArticlesRepository, article1Id: Int, article2Id: Int, user1Id: Int, user2Id: Int) = {
    for {
      _ <- articleRepo.makeFavorite(article1Id, user1Id)
      _ <- articleRepo.makeFavorite(article1Id, user2Id)
      _ <- articleRepo.makeFavorite(article2Id, user2Id)
    } yield ()
  }

  def prepareDataForListingArticles = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      profileRepo <- ZIO.service[ProfilesRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      user2 <- userRepo.findByEmail(exampleUser2.email).someOrFail(s"User with email ${exampleUser2.email} doesn't exist.")
      _ <- profileRepo.follow(user1.userId, user2.userId)
      _ <- articleRepo.add(exampleArticle1, user1.userId)
      _ <- articleRepo.add(exampleArticle2, user1.userId)
      _ <- articleRepo.add(exampleArticle3, user2.userId)
      article1 <- articleRepo.findArticleBySlug(exampleArticle1Slug).someOrFail(s"Article $exampleArticle1Slug doesn't exist")
      article2 <- articleRepo.findArticleBySlug(exampleArticle2Slug).someOrFail(s"Article $exampleArticle2Slug doesn't exist")
      _ <- prepareTags(articleRepo, article1.articleId, article2.articleId)
      _ <- prepareFavorites(articleRepo, article1.articleId, article2.articleId, user1.userId, user2.userId)
    } yield ()
  }

  def prepareDataForFeedingArticles = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      profileRepo <- ZIO.service[ProfilesRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
      _ <- userRepo.add(exampleUser3)
      _ <- userRepo.add(exampleUser4)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      user2 <- userRepo.findByEmail(exampleUser2.email).someOrFail(s"User with email ${exampleUser2.email} doesn't exist.")
      user3 <- userRepo.findByEmail(exampleUser3.email).someOrFail(s"User with email ${exampleUser3.email} doesn't exist.")
      user4 <- userRepo.findByEmail(exampleUser4.email).someOrFail(s"User with email ${exampleUser4.email} doesn't exist.")
      _ <- profileRepo.follow(user1.userId, user2.userId)
      _ <- profileRepo.follow(user3.userId, user2.userId)
      _ <- articleRepo.add(exampleArticle1, user1.userId)
      _ <- articleRepo.add(exampleArticle2, user1.userId)
      _ <- articleRepo.add(exampleArticle3, user2.userId)
      _ <- articleRepo.add(exampleArticle4, user2.userId)
      _ <- articleRepo.add(exampleArticle5, user3.userId)
      _ <- articleRepo.add(exampleArticle6, user4.userId)
      article1 <- articleRepo.findArticleBySlug(exampleArticle1Slug).someOrFail(s"Article $exampleArticle1Slug doesn't exist")
      article2 <- articleRepo.findArticleBySlug(exampleArticle2Slug).someOrFail(s"Article $exampleArticle2Slug doesn't exist")
      _ <- prepareTags(articleRepo, article1.articleId, article2.articleId)
      _ <- prepareFavorites(articleRepo, article1.articleId, article2.articleId, user1.userId, user2.userId)
    } yield ()
  }

  def prepareDataForGettingArticle = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      _ <- userRepo.add(exampleUser2)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      user2 <- userRepo.findByEmail(exampleUser2.email).someOrFail(s"User with email ${exampleUser2.email} doesn't exist.")
      _ <- articleRepo.add(exampleArticle1, user1.userId)
      _ <- articleRepo.add(exampleArticle2, user2.userId)
      article1 <- articleRepo.findArticleBySlug(exampleArticle1Slug).someOrFail(s"Article $exampleArticle1Slug doesn't exist")
      article2 <- articleRepo.findArticleBySlug(exampleArticle2Slug).someOrFail(s"Article $exampleArticle2Slug doesn't exist")
      _ <- prepareTags(articleRepo, article1.articleId, article2.articleId)
      _ <- prepareFavorites(articleRepo, article1.articleId, article2.articleId, user1.userId, user2.userId)
    } yield ()
  }

  def prepareDataForArticleCreation = {
    for {
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
    } yield ()
  }

  def prepareDataForCreatingNameConflict = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      _ <- articleRepo.add(exampleArticle2, user1.userId)
    } yield ()
  }

  def prepareDataForArticleDeletion = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      _ <- articleRepo.add(exampleArticle1, user1.userId)
      _ <- articleRepo.add(exampleArticle2, user1.userId)
      _ <- articleRepo.add(exampleArticle3, user1.userId)
    } yield ()
  }

  def prepareDataForArticleUpdating = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      _ <- articleRepo.add(exampleArticle1, user1.userId)
    } yield ()
  }

  def prepareDataForUpdatingNameConflict = {
    for {
      articleRepo <- ZIO.service[ArticlesRepository]
      userRepo <- ZIO.service[UsersRepository]
      _ <- userRepo.add(exampleUser1)
      user1 <- userRepo.findByEmail(exampleUser1.email).someOrFail(s"User with email ${exampleUser1.email} doesn't exist.")
      _ <- articleRepo.add(exampleArticle1, user1.userId)
      _ <- articleRepo.add(exampleArticle2, user1.userId)
    } yield ()
  }