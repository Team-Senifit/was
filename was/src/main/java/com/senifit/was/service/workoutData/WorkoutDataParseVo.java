package com.senifit.was.service.workoutData;

import com.senifit.was.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class ProgramParseVo
        extends BaseHashMapParseVo<Program> {}

class ProgramBundleParseVo
        extends BaseCollectionParseVo<ProgramBundle> {}

class ProgramBundleListParseVo
        extends BaseHashMapCollectionParseVo<Program, ProgramBundle> {}

class BundleParseVo
        extends BaseHashMapParseVo<Bundle> {}

class BundleVideoParseVo
        extends BaseCollectionParseVo<BundleVideo> {}

class BundleVideoListParseVo
        extends BaseHashMapCollectionParseVo<Bundle, BundleVideo> {}

class VideoParseVo
        extends BaseHashMapParseVo<Video> {}


interface BaseParseVo<V> {
   Collection<V> toCollection();
}

class BaseHashMapParseVo<V>
    extends HashMap<Long, V>
    implements BaseParseVo<V>
{
    @Override
    public Collection<V> toCollection() {
       return super.values();
    }
}

class BaseCollectionParseVo<V>
    extends ArrayList<V>
    implements BaseParseVo<V>
{
    @Override
    public Collection<V> toCollection() {
        return this;
    }
}

class BaseHashMapCollectionParseVo<K, V>
    extends HashMap<K, ArrayList<V>>
    implements BaseParseVo<V>
{
    @Override
    public Collection<V> toCollection() {
        Collection<V> collections = new ArrayList<>();
        for (ArrayList<V> list : super.values()) {
            collections.addAll(list);
        }
        return collections;
    }
}
