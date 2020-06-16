package com.bjjy.buildtalk.ui.discover;

import com.bjjy.buildtalk.base.presenter.IPresenter;
import com.bjjy.buildtalk.base.view.IView;
import com.bjjy.buildtalk.core.http.response.BaseResponse;
import com.bjjy.buildtalk.entity.ActivityEntity;
import com.bjjy.buildtalk.entity.BannerEntity;
import com.bjjy.buildtalk.entity.CourseEntity;
import com.bjjy.buildtalk.entity.DiscoverEntity;
import com.bjjy.buildtalk.entity.DissertationEntity;
import com.bjjy.buildtalk.entity.EveryTalkEntity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.MasterListEntity;
import com.bjjy.buildtalk.entity.SongsEntity;
import com.bjjy.buildtalk.entity.ThemeInfoEntity;

import java.util.List;

/**
 * @author power
 * @date 2019/4/26 4:53 PM
 * @project BuildTalk
 * @description:
 */
public class DiscoverContract {
    interface View extends IView{
        void handlerDiscoverType(List<DiscoverEntity> discoverEntityList);

        void handlerBanner(List<BannerEntity> bannerEntities);

        void handlerEveryTalk(List<EveryTalkEntity> everyTalkEntities);

        void handlerToptic(CourseEntity courseEntities);

        void handlerCourse(CourseEntity courseEntities);

        void handlerActivitySuccess(ActivityEntity activityEntity);

        void handlerDissertation(List<DissertationEntity> dissertationEntities);

        void handlerSongs(List<SongsEntity> songsEntities, int position);

        void handlerAuthor(MasterListEntity masterListEntity);

        void handlerAttrntion(BaseResponse<IEntity> baseResponse, List<MasterListEntity.MasterInfoBean> mList, int i);

        void handlerEssence(List<ThemeInfoEntity.ThemeInfoBean> themeInfoBeanList);
    }

    interface Presenter extends IPresenter<View>{

    }
}
